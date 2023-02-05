import openai
import requests
import json
from twilio.rest import Client
from flask import Flask, request

# Initialize the OpenAI API Key
openai.api_key = "your_openai_api_key"

# Initialize the Twilio API Client
account_sid = "your_twilio_account_sid"
auth_token = "your_twilio_auth_token"
client = Client(account_sid, auth_token)

# Function to generate a response from ChatGPT
def generate_response(prompt):
    completions = openai.Completion.create(
        engine="text-davinci-002",
        prompt=prompt,
        max_tokens=1024,
        n=1,
        stop=None,
        temperature=0.5,
    )
    message = completions.choices[0].text
    return message
    
# Function to handle incoming messages from Twilio
def handle_message(request):
    # Extract the message text from the request
    incoming_message = request.values.get("Body", None)
    sender = request.values.get("From", None)

    # Use the incoming message to generate a response from ChatGPT
    response_message = generate_response(incoming_message)
    
    # Send the response back to the user via Twilio's WhatsApp API
    message = client.messages.create(
        to="whatsapp:" + sender,
        from_="whatsapp:your_twilio_whatsapp_number",
        body=response_message
    )
    
    # Return a success status code
    return ("", 204)

# Initialize the Flask app
app = Flask(__name__)

# Add a route for incoming messages
app.add_route("/incoming", handle_message, methods=["POST"])

# Save the file as app.py or another name of your choice
