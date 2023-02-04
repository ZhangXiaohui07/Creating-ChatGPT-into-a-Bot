require 'openai'
require 'telegram/bot'

Openai.api_key = "YOUR_OPENAI_API_KEY"
token = 'YOUR_TELEGRAM_BOT_API_TOKEN'

Telegram::Bot::Client.run(token) do |bot|
  bot.listen do |message|
    response = Openai::Completion.create(
      engine: "text-davinci-002",
      prompt: "User: #{message.text}\n ChatGPT: ",
      max_tokens: 100,
      n: 1,
      stop: "",
      temperature: 0.5,
    )

    chat_response = response["choices"][0]["text"].strip
    bot.api.send_message(chat_id: message.chat.id, text: chat_response)
  end
end
