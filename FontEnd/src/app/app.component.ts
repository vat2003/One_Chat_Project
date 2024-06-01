import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MenuSideComponent } from './client/menu-side/menu-side.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [CommonModule, RouterOutlet, MenuSideComponent],
})
export class AppComponent {
  title = 'FontEnd';
}
