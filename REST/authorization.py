from flask import request, abort
from functools import wraps
import encrypt_password as encrypt
import settings as settings


def check_auth(_request):
    auth = _request.authorization
    print(auth)
    if auth:
        config = settings.config
        db = settings.db
        conn = db.connect(**config)
        cursor = conn.cursor()
        sql_string = 'select Password from `users` where Login=%s'
        username = auth.username
        password = auth.password
        cursor.execute(sql_string, (username,))
        result = cursor.fetchone()
        conn.close()
        if result is not None:
            stored_password = result[0]
            return encrypt.check_password(password, stored_password)


def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        if check_auth(request):
            return f(*args, **kwargs)
        return auth_error()
    return decorated


@requires_auth
def get_user_permissions(_request):
    config = settings.config
    db = settings.db
    conn = db.connect(**config)
    cursor = conn.cursor()
    sql_string = 'select Admin from `users` where Login=%s'
    username = _request.authorization.username
    cursor.execute(sql_string, (username,))
    permission = cursor.fetchone()[0]
    conn.close()
    if permission == 1:
        return 'admin'
    elif permission == 0:
        return 'user'


def requires_roles(*roles):
    def wrapper(f):
        @wraps(f)
        def wrapped(*args, **kwargs):
            if get_user_permissions(request) not in roles:
                return permission_error()
            return f(*args, **kwargs)
        return wrapped
    return wrapper


def auth_error():
    abort(401)


def permission_error():
    abort(403)


def reset_root_user():
    config = settings.config
    db = settings.db
    conn = db.connect(**config)
    cursor = conn.cursor()
    sql_string = "select ID from `users` where Login='root'"
    cursor.execute(sql_string)
    result = cursor.fetchone()
    password = encrypt.password_to_store('root_password')
    if result is not None:
        _id = result[0]
        sql_string = """update `users` set Password='{}' 
                            where ID={}""".format(password, _id)
    else:
        sql_string = """insert into `users` (Login, Name, Admin, Password)
                            values 
                            ('root', 'Root', '1', '{}')""".format(password)
    cursor.execute(sql_string)
    conn.commit()
    conn.close()


if __name__ == '__main__':
    reset_root_user()
