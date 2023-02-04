import openai
import telegram
from telegram.ext import Updater, CommandHandler, MessageHandler, Filters

openai.api_key = "YOUR_OPENAI_API_KEY"

# Initialize the bot using token
bot = telegram.Bot(token="YOUR_TELEGRAM_BOT_TOKEN")

def chat(message):
    model_engine = "text-davinci-002"
    prompt = (f"{message}")

    completions = openai.Completion.create(
        engine=model_engine,
        prompt=prompt,
        max_tokens=1024,
        n=1,
        stop=None,
        temperature=0.5,
    )

    message = completions.choices[0].text
    return message.strip()

def handle_message(bot, update):
    message = update.message.text
    response = chat(message)
    bot.send_message(chat_id=update.message.chat_id, text=response)

def start(bot, update):
    bot.send_message(chat_id=update.message.chat_id, text="Hi there! I'm a chatbot powered by OpenAI. How can I help you today?")

def help(bot, update):
    bot.send_message(chat_id=update.message.chat_id, text="I'm here to answer your questions and have a conversation with you. Just type in your question and I'll do my best to respond!")

# Start the dispatcher
updater = Updater(bot.token)
dp = updater.dispatcher
dp.add_handler(CommandHandler("start", start))
dp.add_handler(CommandHandler("help", help))
dp.add_handler(MessageHandler(Filters.text, handle_message))
updater.start_polling()
updater.idle()
