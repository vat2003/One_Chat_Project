import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {MatToolbar, MatToolbarModule} from '@angular/material/toolbar';
import {MatCard, MatCardContent, MatCardModule, MatCardTitle} from '@angular/material/card';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input'; // Import MatInputModule
import {MatButtonModule} from '@angular/material/button'; // Import MatButtonModule

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
    MatButtonModule // Thêm MatButtonModule
  ],
  templateUrl: './chat-layout.component.html',
  styleUrls: ['./chat-layout.component.css'] // Cập nhật thành styleUrls
})
export class ChatLayoutComponent {

  messages = [
    {user: 'Người dùng 1', text: 'Xin chào!'},
    {user: 'Người dùng 2', text: 'Chào bạn!'}
  ];
  messageText = '';

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
