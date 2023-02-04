const TelegramBot = require('node-telegram-bot-api');
const axios = require('axios');
const nlp = require('compromise');

const token = 'YOUR_TELEGRAM_BOT_TOKEN';
const openai_api_key = 'YOUR_OPENAI_API_KEY';

const bot = new TelegramBot(token, { polling: true });
const cache = {};

bot.on('message', (msg) => {
  const chatId = msg.chat.id;
  const nlpResult = nlp(msg.text);

  const intent = nlpResult.topics().out('text');
  const entities = nlpResult.entities().out('text');

  if (cache[msg.text]) {
    bot.sendMessage(chatId, cache[msg.text]);
    return;
  }

  axios.post('https://api.openai.com/v1/engines/davinci/jobs', {
    prompt: msg.text,
    max_tokens: 100
  }, {
    headers: {
      'Authorization': `Bearer ${openai_api_key}`,
      'Content-Type': 'application/json'
    },
    timeout: 5000
  })
  .then(response => {
    const responseText = response.data.choices[0].text;
    cache[msg.text] = responseText;
    bot.sendMessage(chatId, responseText);
  })
  .catch(error => {
    console.error(JSON.stringify({
      message: error.message,
      stack: error.stack
    }, null, 2));
    bot.sendMessage(chatId, 'An error occured, please try again later.');
  });

  console.log(`Intent: ${intent}`);
  console.log(`Entities: ${entities}`);
});

