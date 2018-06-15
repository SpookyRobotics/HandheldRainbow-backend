sudo echo 20 > /sys/class/gpio/export
sudo echo 21 > /sys/class/gpio/export
sleep 1
sudo echo out > /sys/class/gpio/gpio20/direction
sudo echo 1 > /sys/class/gpio/gpio20/value
sudo echo out > /sys/class/gpio/gpio21/direction
sudo echo 1 >  /sys/class/gpio/gpio21/value
