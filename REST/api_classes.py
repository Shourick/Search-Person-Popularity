from flask_restful import Resource, Api
import REST.encrypt_password as encrypt
import REST.settings as settings
from REST.authorization import requires_auth, requires_roles


class WorkResource(Resource):
    config = settings.config
    db = settings.db

    @classmethod
    def __init__(cls):
        cls.conn = cls.db.connect(**cls.config)
        cls.cursor = cls.conn.cursor()


class Dictionary(WorkResource):
    table = 'some_table'

    @classmethod
    @requires_auth
    def get(cls, _id=None, _name=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        keys = ['id', 'name']
        if _id is not None:
            cursor.execute(
                "select ID, Name from `{}` where ID=%s".format(table),
                (_id,)
            )
        elif _name is not None:
            cursor.execute(
                "select ID, Name from `{}` where Name=%s".format(table), (_name,)
            )
        else:
            cursor.execute("select ID, Name from `{}`".format(table))
        result = [dict(zip(keys, data)) for data in cursor.fetchall()]
        conn.close()
        return result

    @classmethod
    @requires_roles('admin')
    def put(cls, _name=None, person_id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if person_id is not None:
            cursor.execute(
                "insert into `{}` (Name, PersonID) values (%s, %s)".format(table),
                (_name, person_id))
        else:
            cursor.execute(
                "insert into `{}` (Name) values (%s)".format(table),
                (_name,))
        conn.commit()
        result = cursor.lastrowid
        conn.close()
        return result

    @classmethod
    @requires_roles('admin')
    def post(cls, _id=None, _name=None, person_id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if person_id is not None:
            cursor.execute(
                "update `{}` set Name=%s, PersonID=%s where ID=%s".format(table),
                (_name, person_id, _id)
            )
        else:
            cursor.execute(
                "update `{} set Name=%s where ID=%s".format(table),
                (_name, _id)
            )
        conn.commit()
        conn.close()

    @classmethod
    @requires_roles('admin')
    def delete(cls, _id=None):
        # cls.__init__()
        conn = cls.conn
        cursor = cls.cursor
        table = cls.table
        if _id is not None:
            cursor.execute("delete from `{}` where ID=%s".format(table),
            (_id,))
        else:
            cursor.execute("delete from `{}`".format(table))
        conn.commit()
        conn.close()


class Persons(Dictionary):
    table = 'persons'


class Keywords(Dictionary):
    table = 'keywords'

    @requires_auth
    def get(self, _id=None, _name=None, person_id=None, person_name=None):
        # self.__init__()
        conn = self.conn
        cursor = self.cursor
        table = self.table
        keys = ['id', 'name', 'person_id']
        if _id is None and _name is None and \
           person_id is None and person_name is None:
            cursor.execute(
                """select t1.ID, t1.Name, t1.PersonID from `{}` t1
                    join persons t2 on t1.PersonID=t2.ID""".format(table)
            )
        else:
            if person_id is not None:
                cursor.execute(
                    """select ID, Name, PersonID from `{}` 
                        where PersonID=%s""".format(table), (person_id,)
                )
            elif person_name is not None:
                cursor.execute(
                    """select t1.ID, t1.Name, t1.PersonID from `{}` t1
                        join persons t2 on t1.PersonID=t2.ID
                        where t2.Name=%s""".format(table), (person_name,)
                )
            elif _id is not None:
                cursor.execute(
                    """select ID, Name, PersonID from `{}` 
                        where ID=%s""".format(table), (_id,)
                )
            elif _name is not None:
                cursor.execute(
                    """select ID, Name, PersonID from `{}` 
                        where Name=%s""".format(table), (_name,)
                )
        result = [dict(zip(keys, data)) for data in cursor.fetchall()]
        conn.close()
        return result


class Sites(Dictionary):
    table = 'sites'


class PersonPageRank(WorkResource):
    table = 'personpagerank'

    @requires_auth
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

    @requires_roles('admin')
    def get(self, _id=None, _login=None, _admin=None):
        conn = self.conn
        cursor = self.cursor
        table = self.table
        keys = ['id', 'login', 'name', 'admin']
        sql_str = "select ID, Login, Name from `{}`{{}}".format(table)

        if _id is not None:
            sql_str = sql_str.format(" where ID='{}'".format(_id))
        elif _login is not None:
            sql_str = sql_str.format(" where Login='{}'".format(_login))
        elif _admin is not None:
            sql_str = sql_str.format(" where Admin='{}'".format(_admin))
        else:
            sql_str = sql_str.format('')
        cursor.execute(sql_str)
        result = [dict(zip(keys, data)) for data in cursor.fetchall()]
        conn.close()
        return result

    @requires_roles('admin')
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
        result = cursor.lastrowid
        conn.close()
        return result

    @requires_roles('admin')
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
                where ID={}""".format(table, stored_password, _id)
        if sql_str != '':
            cursor.execute(sql_str)
        conn.commit()
        conn.close()

    @requires_roles('admin')
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
            sql_str = "delete from `{}` where Login!='root'".format(table)
        cursor.execute(sql_str)
        conn.commit()
        conn.close()
