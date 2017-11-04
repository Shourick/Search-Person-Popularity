from flask import Flask, request
from flask_restful import Resource, Api
import MySQLdb
from json import dumps

# Create a engine for connecting to SQLite3.
# Assuming salaries.db is in your app root folder

config = {
    'user': 'spp',
    'password': 'spp',
    'database': 'spp',
    'host': 'localhost',
    'charset': 'utf8'
}
db = MySQLdb

app = Flask(__name__)
api = Api(app)


# def get(db):
#     # Connect to databse
#     conn = db.connect(**config)
#     cursor = conn.cursor()
#     # Perform query and return JSON data
#     cursor.execute("select Name from persons")
#     return {'Names': [i[0] for i in cursor.fetchall()]}
#

class Persons(Resource):
    @staticmethod
    def get(person_id=None, person_name=None):
        # Connect to databse
        conn = db.connect(**config)
        cursor = conn.cursor()
        # Perform query and return JSON data
        if person_id:
            cursor.execute("select Name from persons where ID='%s'" % person_id)
        elif person_name:
            if person_name == '@all':
                cursor.execute("select Name from persons")
            else:
                cursor.execute("select ID from persons where Name='%s'" % person_name)
        result = cursor.fetchall()
        conn.close()
        return [i[0] for i in result]

    @staticmethod
    def put(person_name):
        # Connect to databse
        conn = db.connect(**config)
        cursor = conn.cursor()
        cursor.execute("insert into persons (Name) values ('%s')" % person_name)
        conn.commit()
        cursor.execute("select ID from persons where Name='%s'" % person_name)
        result = cursor.fetchone()[0]
        conn.close()
        return result

    @staticmethod
    def post(person_name, person_id):
        conn = db.connect(**config)
        cursor = conn.cursor()
        cursor.execute("update persons set Name='%s' where ID='%s'" %
                       (person_name, person_id)
                       )
        conn.commit()
        conn.close()

    @staticmethod
    def delete(person_id):
        conn = db.connect(**config)
        cursor = conn.cursor()
        cursor.execute("delete from persons where ID='%s'" % person_id)
        conn.commit()
        conn.close()


class Keywords(Resource):
    @staticmethod
    def get(keyword_id=None, keyword_name=None, person_id=None, person_name=None):
        conn = db.connect(**config)
        cursor = conn.cursor()
        if keyword_name == '@all':
            cursor.execute(
                """select t2.Name, t1.Name from keywords t1
                    join persons t2 on t1.PersonID=t2.ID
                """
            )
            result = {}
            for item in cursor.fetchall():
                result.setdefault(item[0], []).append(item[1])
        else:
            if person_id:
                cursor.execute("select Name from keywords where PersonID='%s'" % person_id)
            elif person_name:
                cursor.execute(
                    """select t1.Name from keywords t1
                        join persons t2 on t1.PersonID=t2.ID
                        where t2.Name='%s'""" % person_name
                )
            elif keyword_id:
                cursor.execute("select Name from keywords where ID='%s'" % keyword_id)
            elif keyword_name:
                cursor.execute(
                    """select t2.Name from keywords t1
                        join persons t2 on t1.PersonID=t2.ID
                        where t1.Name='%s'" % keyword_name
                    """
                )
            result = [i[0] for i in cursor.fetchall()]
        conn.close()
        return result


class PersonPageRank(Resource):
    @staticmethod
    def get(person_id=None, site_id=None, start_date=None, end_date=None):
        conn = db.connect(**config)
        cursor = conn.cursor()
        sql_str = """select t1.Rank from personpagerank t1
                        join pages t2 on t1.PageID=t2.ID
                        join persons t3 on t1.PersonID=t3.ID
                        {}
                        where t3.ID='%s'{}"""
        if site_id:
            sql_str = sql_str.format("join sites t4 on t2.SiteID=t4.ID", " and t4.ID='%s'{}")
            if start_date:
                if end_date is None:
                    sql_str = sql_str.format(" and t2.FoundDateTime='%s'")
                    cursor.execute(sql_str % (person_id, site_id, start_date))
                else:
                    sql_str = sql_str.format(
                        " and t2.FoundDateTime>='%s' and t2.FoundDateTime<='%s'"
                    )
                    cursor.execute(sql_str % (person_id, site_id, start_date, end_date))
            else:
                sql_str = sql_str.format("")
                cursor.execute(sql_str % (person_id, site_id))
        else:
            sql_str = sql_str.format("", "")
            cursor.execute(sql_str % person_id)
        result = sum(map(lambda x: x[0], cursor.fetchall()))
        conn.close()
        return result


api.add_resource(
    Keywords,
    '/keywords/<int:keyword_id>', 
    '/keywords/<string:keyword_name>', 
    '/keywords/person/<int:person_id>', 
    '/keywords/person/<string:person_name>', 
)
api.add_resource(
    Persons,
    # '/persons/<int:person_id>',
    '/persons/<int:person_id>',
    '/persons/<string:person_name>',
    '/persons/<int:person_id>/<string:person_name>',
)
api.add_resource(
    PersonPageRank,
    '/rank/<int:person_id>',
    '/rank/<int:person_id>/<int:site_id>',
    '/rank/<int:person_id>/<int:site_id>/<string:start_date>',
    '/rank/<int:person_id>/<int:site_id>/<string:start_date>&<string:end_date>'
)

if __name__ == '__main__':
    # print(db.__dict__)
    # print(get(db))
    app.run()
