#-*- coding:utf-8 -*-

import message_pb2, sd_times_pb2, socket, Client
import google.protobuf.internal.encoder as encoder
import google.protobuf.internal.decoder as decoder
import sys
from random import *


class ProxyUser:
    c1 = Client.Conexao()
    
    def AddTime(self, time):
            try:
                retorno = self.doOperation("BdTimes", "AddTime", time.SerializeToString())
                return retorno           
            except AttributeError:
                pass
    
    
    def List(self,busca):
            try:
                retorno = self.doOperation("BdTimes", "List", busca.SerializeToString())
                times = sd_times_pb2.Times()
                times.ParseFromString(retorno)
                return times
            except AttributeError:
                pass
    
    def AddTitulo(self, alterar):
                try:
                    retorno = self.doOperation("BdTimes", "AddTitulo", alterar.SerializeToString())
                    return retorno
                except AttributeError:
                    pass
    def BuscaTime(self,busca):
            try:
                retorno = self.doOperation("BdTimes", "BuscarTimeNome", busca.SerializeToString())
                time = sd_times_pb2.Time()
                time.ParseFromString(retorno)
                return time
            except AttributeError:
                pass
            
    def RemoveTime(self, remove):
            try:
                retorno = self.doOperation("BdTimes", "RemoveTime", remove.SerializeToString())
                return retorno
            except AttributeError:
                pass
    
    def EmpacotaMensagem(self, referencia, metodo, objeto):
            message = message_pb2.Message()
            message.type = 0
            message.id = randint(0,1000)
            message.objReference = referencia
            message.methodId = metodo
            message.arguments = objeto
            out = message.SerializeToString()
            out = encoder._VarintBytes(len(out)) + out
            return out
            
    def DesempacotaMensagem(self, msg):
            message = message_pb2.Message()
            # Read length
            (size, position) = decoder._DecodeVarint(msg, 0)
            # Read the message
            message.ParseFromString(msg[position:position+size])
            
            return message.arguments
        
    def doOperation(self, objReference, method, args):
            data = self.EmpacotaMensagem(objReference, method, args)
            self.c1.sendResponse(data)
            self.c1.udp.settimeout(2)
            count = 3;
            while count >= 0:
                try:
                    resposta = self.DesempacotaMensagem(self.c1.getRequest())
                    return resposta
                except socket.timeout:
                    if count >0: print "Retransmitido"
                    if count == 0: 
                        print "Contate o ADM da rede"
                        sys.exit()
                    count -=1
                    continue