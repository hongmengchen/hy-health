<template>
  <div>
    <!-- AI 小助手按钮 -->
    <div class="ai-float-btn" @click="toggleChat" title="AI 小助手">
      <el-icon><ChatDotRound /></el-icon>
    </div>

    <!-- AI 小助手聊天框固定悬浮 -->
    <div class="ai-chat-box" v-show="aiDialogVisible" ref="chatWindow">
      <div class="chat-header">
        AI 小助手
        <button class="close-btn" @click="aiDialogVisible = false">×</button>
      </div>
      <div class="chat-window">
        <div
          v-for="(msg, index) in chatMessages"
          :key="index"
          class="chat-msg"
          :class="msg.role"
        >
          <span>{{ msg.role === 'user' ? '你：' : '助手：' }}</span>{{ msg.content }}
        </div>
      </div>
      <div class="chat-input-wrapper">
        <el-input
          v-model="chatInput"
          placeholder="请输入问题..."
          @keyup.enter="sendMessage"
          clearable
        />
        <el-button type="primary" @click="sendMessage" :loading="loading">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { ChatDotRound } from '@element-plus/icons-vue';
import axios from 'axios';

export default {
  name: "AiAssistant",
  components: { ChatDotRound },
  data() {
    return {
      aiDialogVisible: false,
      chatMessages: [],
      chatInput: '',
      loading: false,   // 发送中状态
    };
  },
  methods: {
    toggleChat() {
      this.aiDialogVisible = !this.aiDialogVisible;
      if (this.aiDialogVisible) {
        this.$nextTick(() => {
          const el = this.$refs.chatWindow;
          if (el) el.scrollTop = el.scrollHeight;
        });
      }
    },

    async sendMessage() {
      const content = this.chatInput.trim();
      if (!content) return;

      // 用户消息加入聊天
      this.chatMessages.push({ role: 'user', content });
      this.chatInput = '';
      this.loading = true;

      this.$nextTick(() => {
        const el = this.$refs.chatWindow;
        if (el) el.scrollTop = el.scrollHeight;
      });

      try {
        // 调用 AI API，替换为你自己的接口地址和参数
        const response = await axios.post(
            'https://api.moonshot.cn/v1/chat/completions',
            {
              model: "moonshot-v1-8k",  // 或者其他模型
              messages: [
                {role: "system", content: "你是一个有帮助的AI助手。"},
                ...this.chatMessages.map(msg => ({
                  role: msg.role === 'user' ? 'user' : 'assistant',
                  content: msg.content
                })),
                {role: "user", content: content}
              ],
              max_tokens: 500,
              temperature: 0.7,
            },
            {
              headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer XXXX`, // 替换成你的API Key
              }
            }
        );

        // 取第一个回答
        const aiReply = response.data.choices[0].message.content.trim();
        this.chatMessages.push({ role: 'ai', content: aiReply });

      } catch (error) {
        console.error("AI 请求失败:", error);
        this.chatMessages.push({ role: 'ai', content: '抱歉，AI 服务暂时不可用，请稍后重试。' });
      } finally {
        this.loading = false;
        this.$nextTick(() => {
          const el = this.$refs.chatWindow;
          if (el) el.scrollTop = el.scrollHeight;
        });
      }
    },
  },
};
</script>


<style scoped>
.ai-float-btn {
  position: fixed;
  right: 40px;
  bottom: 40px;
  width: 56px;
  height: 56px;
  background-color: #409EFF;
  color: white;
  border-radius: 50%;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  z-index: 9999;
  transition: background-color 0.3s;
}
.ai-float-btn:hover {
  background-color: #337ecc;
}
.ai-float-btn .el-icon {
  font-size: 28px;
}
.ai-chat-box {
  position: fixed;
  right: 110px;
  bottom: 40px;
  width: 400px;
  max-height: 500px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 12px 26px rgba(16, 30, 115, 0.2);
  display: flex;
  flex-direction: column;
  z-index: 10000;
  font-size: 14px;
}
.chat-header {
  background: #409eff;
  color: white;
  font-weight: 600;
  padding: 10px;
  border-radius: 8px 8px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.close-btn {
  background: transparent;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
  line-height: 1;
  padding: 0;
}
.chat-window {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: #f5f7fa;
}
.chat-msg {
  margin-bottom: 8px;
  word-break: break-word;
}
.chat-msg.user {
  text-align: right;
  color: #333;
}
.chat-msg.ai {
  text-align: left;
  color: #409eff;
}
.chat-input-wrapper {
  display: flex;
  gap: 8px;
  padding: 10px;
  border-top: 1px solid #eee;
}
</style>