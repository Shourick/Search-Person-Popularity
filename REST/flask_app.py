from flask import Flask
from flask_restful import Api
from api_classes import Persons, Keywords, Sites, PersonPageRank, Users


app = Flask(__name__)
api = Api(app)

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
