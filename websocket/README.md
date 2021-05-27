
##<center>WebSocket Demo Resolve</center>
- #####SendToMethodReturnValueHandler

- #####应用程序可以发送针对特定用户的消息，Spring的STOMP支持可识别"/user/"为此目的而作为前缀的目标。例如，客户端可能订阅目标"/user/queue/position-updates"。该目的地将由该处理UserDestinationMessageHandler并且转换为用户会话唯一的目的地，例如"/queue/position-updates-user123"。这提供了订阅一般命名的目的地的便利性，同时确保不与订阅相同目的地的其他用户发生冲突，使得每个用户可以接收唯一的库存位置更新。
  
  在发送侧，可以将消息发送到目的地，例如"/user/{username}/queue/position-updates"，该目的地 将由UserDestinationMessageHandler一个或多个目的地翻译，一个目的地用于与用户相关联的每个会话。这允许应用程序中的任何组件发送针对特定用户的消息，而不必知道除其名称和通用目标之外的任何内容。通过注释和消息传递模板也支持这一点。