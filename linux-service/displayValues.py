import client as u
import sys

u.connect()
u.clear()


for index in range(1, len(sys.argv)):
    rgbValue = sys.argv[index]
    diodeIndex = index - 1
    x = diodeIndex % 8
    y = diodeIndex / 8
    r = rgbValue & 0xFF0000
    g = rgbValue & 0x00FF00
    b = rgbValue & 0x0000FF
    u.set_pixel(x, y, r, g, b)
u.show()
