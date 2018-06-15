sudo echo 3 > /sys/class/gpio/export
sudo echo 2 > /sys/class/gpio/export
sleep 1
sudo echo out > /sys/class/gpio/gpio2/direction
sudo echo 1 > /sys/class/gpio/gpio2/value
sudo echo out > /sys/class/gpio/gpio3/direction
sudo echo 1 >  /sys/class/gpio/gpio3/value
