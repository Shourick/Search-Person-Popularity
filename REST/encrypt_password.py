import random
import hashlib


def salt_generator(_length=16):
    return ''.join(chr(random.randint(32, 123)) for _ in range(_length))


def encrypt_password(_password, _salt=None):
    if _salt is None:
        _salt = salt_generator(16)
    concat_str_encoded = (_password + _salt).encode('utf-8')
    encrypted_password = hashlib.sha256(concat_str_encoded).hexdigest()
    return encrypted_password, _salt


def password_to_store(_password):
    _salt = salt_generator(16)
    return '|'.join(encrypt_password(_password, _salt))


def check_password(_password, _stored_password):
    hashed_password, salt = _stored_password.split('|')
    if hashed_password == encrypt_password(_password, salt)[0]:
        return True
    return False
