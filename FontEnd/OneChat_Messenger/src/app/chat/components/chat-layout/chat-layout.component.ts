import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {MatToolbar, MatToolbarModule} from '@angular/material/toolbar';
import {MatCard, MatCardContent, MatCardModule, MatCardTitle} from '@angular/material/card';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input'; // Import MatInputModule
import {MatButtonModule} from '@angular/material/button';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatListItem, MatNavList} from "@angular/material/list"; // Import MatButtonModule

@Component({
  selector: 'app-chat-layout',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    MatCardContent,
    MatCard,
    MatCardTitle,
    MatFormField,
    MatToolbar,
    MatFormFieldModule,
    MatCardModule,
    MatToolbarModule,
    MatInputModule, // Thêm MatInputModule
    MatButtonModule,
    MatSidenavContainer,
    MatSidenavContent,
    MatNavList,
    MatListItem,
    MatSidenav
    // Thêm MatButtonModule
  ],
  templateUrl: './chat-layout.component.html',
  styleUrls: ['./chat-layout.component.css'] // Cập nhật thành styleUrls
})
export class ChatLayoutComponent {
  chatList = [
    {name: 'Chat 1', lastMessage: 'Hello there!'},
    {name: 'Chat 2', lastMessage: 'How are you?'},
    // Thêm các cuộc chat khác
  ];

  messages = [
    {user: 'Người dùng 1', text: 'Xin chào!'},
    {user: 'Người dùng 2', text: 'Chào bạn!'}
  ];
  messageText = '';

  selectChat(chat: any) {
    // Thay đổi messages để hiển thị tin nhắn của cuộc chat được chọn
    this.messages = [
      // Các tin nhắn của cuộc chat được chọn
    ];
  }

  sendMessage() {
    if (this.messageText.trim()) {
      this.messages.push({user: 'Bạn', text: this.messageText});
      this.messageText = '';
    }
  }

  logout() {
    console.log('Đăng xuất');
  }

}
