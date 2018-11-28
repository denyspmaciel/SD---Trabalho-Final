#-*- coding:utf-8 -*-

import message_pb2, sd_times_pb2, socket

time = sd_times_pb2.Time()
time.id = 12
time.nome = "Vasco"
time.data = "01-02-48"
time.titulos = "Vice-2000esempre"

print time.SerializeToString()
