import openai
import requests
import json
from twilio.twiml.messaging_response import MessagingResponse

# Initialize the OpenAI API Key
openai.api_key = "your_openai_api_key"

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

    # Use the incoming message to generate a response from ChatGPT
    response_message = generate_response(incoming_message)

    # Return the response as a Twilio XML response
    resp = MessagingResponse()
    resp.message(response_message)
    return str(resp)

# Save the file as app.py or another name of your choice
