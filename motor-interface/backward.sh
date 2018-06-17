if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
   exit 1
fi
./stop.sh
echo 0 > /sys/class/gpio/gpio2/value
sleep $1
./stop.sh
