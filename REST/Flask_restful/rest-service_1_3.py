from flask import Flask, request
from flask_restful import Resource, Api
import MySQLdb
import Flask_restful.encrypt_password as encrypt


app = Flask(__name__)
api = Api(app)

# config = {
#     'user': 'spp',
#     'password': 'spp',
#     'database': 'spp',
#     'host': 'localhost',
#     'charset': 'utf8'
# }
# db = MySQLdb


class WorkResource(Resource):
    config = {
        'user': 'spp',
        'password': 'spp',
        'database': 'spp',
        'host': 'localhost',
        'charset': 'utf8'
    }
    db = MySQLdb

    @classmethod
    def __init__(cls):
        cls.conn = cls.db.connect(**cls.config)
        cls.cursor = cls.conn.cursor()


class Dictionary(WorkResource):
    table = 'some_table'

    @classmethod
    def get(cls, _id=None, _name=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if _id is not None:
            cursor.execute(
                "select Name from `{}` where ID='{}'".format(table, _id)
            )
        elif _name is not None:
            cursor.execute(
                "select ID from `{}` where Name='{}'".format(table, _name)
            )
        else:
            cursor.execute("select Name from `{}`".format(table))
        result = cursor.fetchall()
        conn.close()
        return [i[0] for i in result]

    @classmethod
    def put(cls, _name=None, person_id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if person_id is not None:
            cursor.execute(
                "insert into `{}` (Name, PersonID) values ('{}', '{}')"
                .format(table, _name, person_id))
        else:
            cursor.execute(
                "insert into `{}` (Name) values ('{}')"
                .format(table, _name))
        conn.commit()
        cursor.execute("select ID from `{}` where Name='{}'".format(table, _name))
        result = cursor.fetchone()[0]
        conn.close()
        return result

    @classmethod
    def post(cls, _id=None, _name=None, person_id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if person_id is not None:
            cursor.execute(
                "update `{}` set Name='{}', PersonID='{}' where ID='{}'"
                .format(table, _name, person_id, _id)
            )
        else:
            cursor.execute(
                "update `{}` set Name='{}' where ID='{}'".format(table, _name, _id)
            )
        conn.commit()
        conn.close()

    @classmethod
    def delete(cls, _id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if _id is not None:
            cursor.execute("delete from `{}` where ID='{}'".format(table, _id))
        else:
            cursor.execute("delete from `{}`".format(table))
        conn.commit()
        conn.close()


class Persons(Dictionary):
    table = 'persons'


class Keywords(Dictionary):
    table = 'keywords'

    def get(self, _id=None, _name=None, person_id=None, person_name=None):
        # self.__init__()
        conn = self.conn
        cursor = self.cursor
        table = self.table
        if _id is None and _name is None:
            cursor.execute(
                """select t2.Name, t1.Name from `{}` t1
                    join persons t2 on t1.PersonID=t2.ID
                """.format(table)
            )
            result = {}
            for item in cursor.fetchall():
                result.setdefault(item[0], []).append(item[1])
        else:
            if person_id is not None:
                cursor.execute(
                    "select Name from `{}` where PersonID='{}'".format(table, person_id)
                )
            elif person_name is not None:
                cursor.execute(
                    """select t1.Name from `{}` t1
                        join persons t2 on t1.PersonID=t2.ID
                        where t2.Name='{}'""".format(table, person_name)
                )
            elif _id is not None:
                cursor.execute(
                    "select Name from `{}` where ID='{}'".format(table, _id)
                )
            elif _name is not None:
                cursor.execute(
                    """select t2.Name from `{}` t1
                        join persons t2 on t1.PersonID=t2.ID
                        where t1.Name='{}'""".format(table, _name)
                )
            result = [i[0] for i in cursor.fetchall()]
        conn.close()
        return result


class Sites(Dictionary):
    table = 'sites'


class PersonPageRank(WorkResource):
    table = 'personpagerank'

    def get(self, person_id=None, site_id=None, start_date=None, end_date=None):
        # self.__init__()
        conn = self.conn
        cursor = self.cursor
        table = self.table
        sql_str = """select t1.Rank from `{0}` t1
                        join pages t2 on t1.PageID=t2.ID
                        join persons t3 on t1.PersonID=t3.ID
                        {{}}
                        where t3.ID='{1}'{{}}""".format(table, person_id)
        if site_id is not None:
            sql_str = sql_str.format(
                "join sites t4 on t2.SiteID=t4.ID",
                " and t4.ID='{}'{{}}".format(site_id)
            )
            if start_date is not None:
                if end_date is None:
                    sql_str = sql_str.format(
                        " and t2.FoundDateTime='{}'".format(start_date)
                    )
                    cursor.execute(sql_str)
                else:
                    sql_str = sql_str.format(
                        " and t2.FoundDateTime>='{}' and t2.FoundDateTime<='{}'"
                        .format(start_date, end_date)
                    )
                    cursor.execute(sql_str)
            else:
                cursor.execute(sql_str.format(''))
        else:
            cursor.execute(sql_str.format('', ''))
        result = sum(map(lambda x: x[0], cursor.fetchall()))
        conn.close()
        return result


class Users(WorkResource):
    table = 'users'

    def get(self, _id=None, _login=None, _admin=None):
        conn = self.conn
        cursor = self.cursor
        table = self.table
        keys = ['id', 'login', 'name', 'admin', 'password']
        sql_str = "select ID, Login, Name, Admin, Password from `{}`{{}}".format(table)

        if _id is not None:
            sql_str = sql_str.format(" where ID='{}'".format(_id))
        elif _login is not None:
            sql_str = sql_str.format(" where Login='{}'".format(_login))
        elif _admin is not None:
            sql_str = sql_str.format(" where Admin='{}'".format(_admin))
        else:
            sql_str = sql_str.format('')
        print(sql_str)
        cursor.execute(sql_str)
        result = [dict(zip(keys, data)) for data in cursor.fetchall()]
        conn.close()
        return result

    def put(self, _login, _name, _admin, _password):
        conn = self.conn
        cursor = self.cursor
        table = self.table
        stored_password = encrypt.password_to_store(_password)
        sql_str = """
            insert into `{}` (Login, Name, Admin, Password)
            values 
            ('{}', '{}', '{}', '{}')""".format(
            table, _login, _name, _admin, stored_password
        )
        cursor.execute(sql_str)
        conn.commit()
        conn.close()

    def post(self, _id, _name=None, _admin=None, _password=None):
        conn = self.conn
        cursor = self.cursor
        table = self.table
        sql_str = ''
        if _password is None:
            if _name is not None and _admin is not None:
                sql_str = """update `{}` set Name='{}', Admin='{}' 
                    where ID='{}'""".format(table, _name, _admin, _id)
            else:
                if _name is not None:
                    sql_str = """update `{}` set Name='{}' 
                        where ID='{}'""".format(table, _name, _id)
                else:
                    sql_str = """update `{}` set Admin='{}' 
                        where ID='{}'""".format(table, _admin, _id)
        else:
            stored_password = encrypt.password_to_store(_password)
            sql_str = """update `{}` set Password='{}' 
                where ID='{}'""".format(table, stored_password, _id)
        if sql_str != '':
            cursor.execute(sql_str)
        conn.commit()
        conn.close()

    def delete(self, _id=None, _login=None, _admin=None):
        conn = self.conn
        cursor = self.cursor
        table = self.table
        if _id is not None:
            sql_str = "delete from `{}` where ID='{}'".format(table, _id)
        elif _login is not None:
            sql_str = "delete from `{}` where Login='{}'".format(table, _login)
        elif _admin is not None:
            sql_str = "delete from `{}` where Admin='{}'".format(table, _admin)
        else:
            sql_str = "delete from `{}`".format(table)
        cursor.execute(sql_str)
        conn.commit()
        conn.close()


api.add_resource(
    Keywords,
    '/keywords',
    '/keywords/<int:_id>',
    '/keywords/<string:_name>',
    '/keywords/<int:person_id>/<string:_name>',
    '/keywords/<int:_id>/<int:person_id>/<string:_name>',
    '/keywords/person/<int:person_id>',
    '/keywords/person/<string:person_name>',
)
api.add_resource(
    Persons,
    '/persons',
    '/persons/<int:_id>',
    '/persons/<string:_name>',
    '/persons/<int:_id>/<string:_name>',
)
api.add_resource(
    Sites,
    '/sites',
    '/sites/<int:_id>',
    '/sites/<string:_name>',
    '/sites/<int:_id>/<string:_name>',
)
api.add_resource(
    PersonPageRank,
    '/rank/<int:person_id>',
    '/rank/<int:person_id>/<int:site_id>',
    '/rank/<int:person_id>/<int:site_id>/<string:start_date>',
    '/rank/<int:person_id>/<int:site_id>/<string:start_date>&<string:end_date>'
)
api.add_resource(
    Users,
    '/users',
    '/users/<int:_id>',
    '/users/<string:_login>',
    '/users/admin/<int:_admin>',
    '/users/<string:_login>&<string:_name>&<int:_admin>&<string:_password>',
    '/users/<int:_id>&<string:_name>&<int:_admin>',
    '/users/<int:_id>&<string:_name>',
    '/users/<int:_id>&<int:_admin>',
    '/users/password/<int:_id>&<string:_password>'
)

if __name__ == '__main__':
    app.run()
