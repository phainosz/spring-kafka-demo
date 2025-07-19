# Kafka Example

This is an example using Spring Cloud Stream and Spring Cloud Function with Kafka.

Start the Docker Compose services, then run the project.

event request example:
```json
{
  "event": "EVENT_A",
  "message": "testing event"
}
```

controller request example:
```bash
curl -X POST http://localhost:8080/api/v1/demo \
     -H "Content-Type: application/json" \
     -d '{"message":"your message here"}'
```