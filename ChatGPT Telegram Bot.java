import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.generics.WebhookBot;

public class ChatGPTBot implements WebhookBot {

  private BotSession session;

  public static void main(String[] args) {
    ApiContextInitializer.init();
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

    try {
      telegramBotsApi.registerBot(new ChatGPTBot());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public BotSession getSession() {
    return session;
  }

  @Override
  public void setSession(BotSession session) {
    this.session = session;
  }

  @Override
  public void onWebhookUpdateReceived(Update update) {
    Message message = update.getMessage();
    if (message != null && message.hasText()) {
      if (message.getText().equals("/start")) {
        sendMessage(message.getChatId(), "Hello, " + message.getFrom().getFirstName());
      } else if (message.getText().equals("/stop")) {
        sendMessage(message.getChatId(), "Goodbye, " + message.getFrom().getFirstName());
      }
    }
  }

  private void sendMessage(long chatId, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(text);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return "ChatGPTBot";
  }

  @Override
  public String getBotToken() {
    return "YOUR_TELEGRAM_BOT_API_TOKEN";
  }
}
