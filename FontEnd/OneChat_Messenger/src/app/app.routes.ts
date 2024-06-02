import { Routes } from '@angular/router';
import {ChatLayoutComponent} from "./chat/components/chat-layout/chat-layout.component";
import {LoginComponent} from "./chat/components/login/login.component";

export const routes: Routes = [

  {path: 'chat', component: ChatLayoutComponent},
  {path: 'login', component: LoginComponent},

];
