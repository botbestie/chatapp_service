#!/bin/bash

# Demo script for Chat App Service
# Make sure the service is running on http://localhost:8080

echo "=== Chat App Service Demo ==="
echo ""

# Test public endpoints
echo "1. Testing public endpoints..."
echo "   Service info:"
curl -s http://localhost:8080/api/public/info | jq '.' 2>/dev/null || curl -s http://localhost:8080/api/public/info
echo ""
echo "   Ping:"
curl -s http://localhost:8080/api/public/ping
echo ""
echo ""

# Test protected endpoints (using default credentials)
echo "2. Testing protected endpoints..."
echo "   Chat health check:"
curl -s -u admin:admin123 http://localhost:8080/api/chat/health
echo ""
echo ""

# Test chat functionality
echo "3. Testing chat functionality..."
echo "   Sending a simple message:"
curl -s -u admin:admin123 "http://localhost:8080/api/chat/send-simple?message=Hello%20world"
echo ""
echo ""

echo "4. Testing full chat message..."
curl -s -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "message": "What is the capital of France?",
    "userId": "demo-user",
    "sessionId": "demo-session-001"
  }' \
  http://localhost:8080/api/chat/send | jq '.' 2>/dev/null || curl -s -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "message": "What is the capital of France?",
    "userId": "demo-user",
    "sessionId": "demo-session-001"
  }' \
  http://localhost:8080/api/chat/send

echo ""
echo ""

echo "5. Testing actuator endpoints..."
echo "   Health check:"
curl -s http://localhost:8080/actuator/health | jq '.' 2>/dev/null || curl -s http://localhost:8080/actuator/health
echo ""

echo "=== Demo completed ==="
echo ""
echo "Note: If you see error messages, make sure:"
echo "1. The service is running on http://localhost:8080"
echo "2. You have set the ANTHROPIC_API_KEY environment variable"
echo "3. The API key is valid and has sufficient credits"
