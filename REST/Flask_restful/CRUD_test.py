import requests
# import json
server = 'shourick.pythonanywhere.com'
server = 'root:root_password@127.0.0.1:5000'

# id = int(requests.put('http://localhost:5000/persons/Наумов').content)
# print(id)
requests.put('http://{}/{}/{}&{}&{}&{}'.format(server, 'users', 'root', 'Alexander', 1, 'root_password'))
# requests.post('http://{}/{}/{}&{}'.format(server, 'users/password', '11', 'password11'))
requests.get('http://{}/{}'.format(server, 'persons'))
requests.delete('http://{}/{}'.format(server, 'users', 1))
# request = requests.get('http://localhost:5000/persons/{}'.format('Шойгу'))
# request = requests.put('http://localhost:5000/keywords/{}'.format('Наумов'))
# print((request.json()))
