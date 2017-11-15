import sys

project_home = u'/var/www/rest' 
if project_home not in sys.path:
    sys.path = [project_home] + sys.path

from flask_app import app as application
