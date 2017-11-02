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
            if person_name == 'all':
                cursor.execute("select Name from persons")
            else:
                cursor.execute("select ID from persons where Name='%s'" % person_name)
        return [i[0] for i in cursor.fetchall()]

    @staticmethod
    def put(person_name):
        # Connect to databse
        conn = db.connect(**config)
        cursor = conn.cursor()
        cursor.execute("insert into persons ('Name') (%s)" % person_name)
        cursor.execute("select ID from persons where Name='%s'" % person_name)
        return cursor.fetchone()




class PersonKeywords(Resource):
    def get(self, person_id):
        conn = db.connect(**config)
        cursor = conn.cursor()
        cursor.execute("select Name from keywords where PersonID='%s'" % person_id)
        query_keywords = cursor.fetchall()
        cursor.execute("select Name from persons where ID='%s'" % person_id)
        query_person_name = cursor.fetchone()
        result = {'person_name': query_person_name,
                  'keywords': [i[0] for i in query_keywords]
                  }
        return result
        # We can have PUT,DELETE,POST here. But in our API GET implementation is sufficient


api.add_resource(PersonKeywords, '/keywords/<int:person_id>')
api.add_resource(Persons,
                 '/persons/<int:person_id>',
                 '/persons/<string:person_name>',
                 '/persons/<int:person_id>/<string:person_name>',
                 )

if __name__ == '__main__':
    # print(db.__dict__)
    # print(get(db))
    app.run()
