# Spark_Performance_Tips
# Install
-----------
    1. Install and setup
    2. install IntelliJ IDEA
    3. install Docker Desktop
    4. open with IntelliJ as an SBT project
# Set up in Local
Start Terminal
---------
    To set up the dockerized Spark cluster we will be using in the course, do the following:
    > cd spark-cluster  # open a terminal and navigate to spark-cluster
    > chmod +x build-images.sh
    > ./build-images.sh
    > docker-compose up --scale spark-worker=3
New Terminal
---------------
    > docker exec -it spark-cluster-spark-master-1 bash
    > run build-images.sh
    > cd /spark/bin
    > ./spark-shell
## Sample cmd used in this repo
-------------
    > docker cp src/main/resources/data spark-cluster-spark-master-1:/tmp
    > /spark/bin/spark-submit --class SparkOpt.TestDeployApp --master spark://3ffba1a9a30f:7077 --deploy-mode client --verbose --supervise /opt/spark-apps/spark-optimization.jar  /opt/spark-data/movies.json  /opt/spark-data/goodcomedy.json

