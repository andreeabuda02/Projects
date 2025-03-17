import pika
import csv
import json
import ssl
import time
import uuid
import argparse
import datetime

# Define function to get command-line arguments
def parse_arguments():
    parser = argparse.ArgumentParser(description="Send sensor data to RabbitMQ")
    
    # Add command-line argument for the device UUID
    parser.add_argument('--device_id', type=str, required=True, help="Device UUID")

    return parser.parse_args()

# Main function to execute the script
def main():
    args = parse_arguments()

    # RabbitMQ connection parameters (these are hardcoded as per your request)
    rabbitmq_host = 'sparrow-01.rmq.cloudamqp.com'
    rabbitmq_queue = 'sensor_measurements_queue'
    rabbitmq_name = 'yncbcspk'
    rabbitmq_password = 'NBPeNXAdO3TATI2xoO_yiPFWn8H8zdAv'
    rabbitmq_port = 5671
    csv_file_path = 'sensor.csv'  # Hardcoded file path (can be modified as needed)
    
    device_id = args.device_id  # Device UUID passed as an argument

    # Validate device UUID format
    try:
        device_id = uuid.UUID(device_id)  # Ensure the device_id is a valid UUID
    except ValueError:
        print(f"Invalid UUID format: {device_id}")
        return

    # Connect to RabbitMQ
    rabbitmq_credentials = pika.PlainCredentials(rabbitmq_name, rabbitmq_password)
    ssl_context = ssl.create_default_context()
    ssl_options = pika.SSLOptions(context=ssl_context)

    connection = pika.BlockingConnection(
        pika.ConnectionParameters(
            host=rabbitmq_host,
            port=rabbitmq_port,
            virtual_host=rabbitmq_name,
            credentials=rabbitmq_credentials,
            ssl_options=ssl_options
        )
    )
    channel = connection.channel()

    # Declare a queue to ensure it exists
    channel.queue_declare(queue=rabbitmq_queue, durable=True)

    # Open and read the CSV file
    with open(csv_file_path, mode='r') as csvfile:
        csvreader = csv.reader(csvfile)  # Reading CSV as a list of rows
        i = 1
        # For each row in the CSV, send it as a message to RabbitMQ
        for row in csvreader:
            currentGmtTime = datetime.datetime.now(datetime.timezone.utc)
            newTime = currentGmtTime + datetime.timedelta(hours=2)
            #timestamp = time.time() * 1000
            timestamp = newTime.timestamp() * 1000
            # Convert each row into a message format
            message = {
                "timestamp": int(timestamp),
                "device_id": str(device_id),
                "measurement_value": float(row[0])
            }
            body = json.dumps(message)
            time.sleep(1)
            
            # Publish the message to the queue
            channel.basic_publish(
                exchange='',
                routing_key=rabbitmq_queue,
                body=body,
                properties=pika.BasicProperties(
                    delivery_mode=2,  # Make message persistent
                )
            )
            print(f"Sent message {i}")
            i += 1

    # Close the RabbitMQ connection
    connection.close()

if __name__ == "__main__":
    main()
