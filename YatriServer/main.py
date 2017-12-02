import webapp2
import jinja2
import MySQLdb
import os
import json
import pprint

template_dir = os.path.dirname(__file__)
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir),
							   autoescape = True)

def render_str(template, **params):
	t = jinja_env.get_template(template)
	return t.render(params)


class BaseHandler(webapp2.RequestHandler):
	def write(self, *a, **kw):
		self.response.content_type = 'application/json'
		self.response.out.write(*a, **kw)

	def render_str(self, template, **params):
		# params['user'] = self.user
		return render_str(template, **params)

	def render(self, template, **kw):
		self.write(self.render_str(template, **kw))

db = MySQLdb.connect(host="localhost",
                     user="root",
                     passwd="dewan123",
                     db="digifest")
cs = db.cursor()


class getUserId(BaseHandler):
	def get(self):
		beaconId = str(self.request.get('beaconId'))
		
		cs.execute('SELECT * FROM guide WHERE beaconId="' + beaconId + '"')
		data = cs.fetchone()
		jsn = {}
		
		if data == None:
			self.write(jsn)
			return
		
		jsn['first_name'] = data[0]
		jsn['last_name'] = data[1]
		jsn['phone'] = data[2]
		jsn['bhamashah'] = data[4]
		jsn['age'] = data[5]
		jsn['gender'] = data[6]

		cs.execute('SELECT * FROM knowingPlaces WHERE userId="' + jsn['phone'] + '"')
		data = cs.fetchall()
		expertise = []
		for xp in data:
			expertise.append(xp[0])

		# language table -> userId = lang
		cs.execute('SELECT userId FROM language WHERE lang="' + jsn['phone'] + '"')
		data = cs.fetchall()
		languages = []
		for xp in data:
			languages.append(xp[0])

		jsn['expertise'] = expertise
		jsn['languages'] = languages

		rslt = json.dumps(jsn)

		self.write(rslt)


class RateGuides(BaseHandler):
	def get(self):
		guideId = str(self.request.get('guideId'))
		rating = str(self.request.get('rating'))

		cs.execute('SELECT * FROM thanksMeter WHERE userId="' + guideId + '"')
		data = cs.fetchone()
		print(data, rating)

		if (rating == None):
			jsn = {}
			jsn['error'] = 'non integar rating'
			self.write(json.dumps(jsn))
			return

		if (data == None):
			data = rating
		else:
			data += rating

		cs.execute('UPDATE thanksMeter SET value="' + data + '" where userId = "' + guideId + '"')



app = webapp2.WSGIApplication([
	('/user_id', getUserId),
	('/rate_them_all', RateGuides),
], debug=True)


def main():
	from paste import httpserver
	httpserver.serve(app, host = '139.59.26.15', port = '8080')

main()
