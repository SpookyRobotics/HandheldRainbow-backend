./gradlew jar
sudo mv build/libs/HandheldRainbow.jar /usr/local/bin/HandheldRainbow.jar
sudo cp linux-service/handheld-rainbow /etc/init.d/handheld-rainbow
sudo update-rc.d handheld-rainbow defaults
mkdir /home/pi/monitors
mkdir /home/pi/RainbowHatInterface
mkdir /home/pi/MotorInterface
cp linux-service/*.py /home/pi/RainbowHatInterface/
cp motor-interface/*.sh /home/pi/MotorInterface
echo "LEFT" > /home/pi/.HANDHELD_RAINBOW_INIT
echo "/home/pi/MotorInterface/exportGpio.sh" >> /home/pi/.bashrc
