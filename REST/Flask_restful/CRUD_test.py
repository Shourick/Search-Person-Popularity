import requests
# import json
server = 'shourick.pythonanywhere.com'

#id = int(requests.put('http://localhost:5000/persons/Наумов').content)
#print(id)
requests.post('http://{}/{}/{}'.format(server, 'persons', 'Путин'))
#request = requests.get('http://localhost:5000/persons/{}'.format('Шойгу'))
requests.delete('http://localhost:5000/keywords/{}'.format(36))
request = requests.put('http://localhost:5000/keywords/{}'.format('Наумов'))
print((request.json()))
