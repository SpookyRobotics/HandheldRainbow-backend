import client as u
import sys

u.connect()
u.clear()


for index in range(1, len(sys.argv)):
    rgbValue = int(sys.argv[index])
    diodeIndex = index - 1
    y = diodeIndex % 8
    x = diodeIndex / 8
    r = (rgbValue & 0xFF0000) >> 16
    g = (rgbValue & 0x00FF00) >> 8
    b = rgbValue & 0x0000FF
    #print "value {} {} index {} x {} y {} r {} g {} b {}".format(rgbValue, hex(rgbValue), index, x, y, r, g,b)
    u.set_pixel(x, y, r, g, b)
u.show()


