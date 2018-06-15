import client as u
from random import randint

u.connect()
u.clear()

for z in range(0, 64):
    x = randint(0, 7)
    y = randint(0, 7)
    r = 0
    g = randint(0, 255)
    b = randint(0, 255)
    u.set_pixel(x, y, r, g, b)

u.show()
