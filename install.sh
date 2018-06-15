# Build and install application
./gradlew jar
sudo mv build/libs/HandheldRainbow.jar /usr/local/bin/HandheldRainbow.jar

# Install services
sudo cp rainbowhat-interface/handheld-rainbow /etc/init.d/handheld-rainbow
sudo update-rc.d handheld-rainbow defaults
sudo cp motor-interface/motor-interface /etc/init.d/motor-interface
sudo update-rc.d motor-interface defaults
sudo cp tower-interface/tower-interface /etc/init.d/tower-interface
sudo update-rc.d tower-interface defaults

# Create control script directories
mkdir /home/pi/monitors
mkdir /home/pi/RainbowHatInterface
mkdir /home/pi/MotorInterface
mkdir /home/pi/TowerInterface
sudo mkdir /usr/local/bin/wheeledPlatform

# Copy all control scripts
cp rainbowhat-interface/*.py /home/pi/RainbowHatInterface/
cp motor-interface/*.sh /home/pi/MotorInterface
cp tower-interface/*.sh /home/pi/TowerInterface
sudo mv /home/pi/MotorInterface/MotorInterface.sh /usr/local/bin/wheeledPlatform/
sudo mv /home/pi/TowerInterface/TowerInterface.sh /usr/local/bin/wheeledPlatform/

# Assign default identifier
echo "LEFT" > /home/pi/.HANDHELD_RAINBOW_INIT
