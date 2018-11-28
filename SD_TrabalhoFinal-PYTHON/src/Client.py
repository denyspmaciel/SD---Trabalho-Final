import socket


class Conexao():
	def __init__(self):
		pass
	
	HOST = socket.gethostbyname("localhost")
	PORT = 6789
	
	udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	dest = (HOST,PORT)
	
	def sendResponse(self, request):
		self.udp.sendto(request, self.dest)
		
	def getRequest(self):
		var = self.udp.recvfrom(1000)
		return var[0]
		
	def Close(self):
		self.udp.close()
		
