./gradlew jar
sudo mv build/libs/HandheldRainbow.jar /usr/local/bin/HandheldRainbow.jar
sudo cp linux-service/handheld-rainbow /etc/init.d/handheld-rainbow
sudo update-rc.d handheld-rainbow defaults
mkdir /home/pi/monitors
mkdir /home/pi/RainbowHatInterface
cp linux-service/*.py /home/pi/RainbowHatInterface/
echo "LEFT" > /home/pi/.HANDHELD_RAINBOW_INIT
