# Computer-Science-Project
PBRU IT CS 654244141

วิธ๊ใช้งาน

database 
1. ให้คงอยู่ใน path dir ที่มี docker-compose.yml
2. เปิด Docker Desktop
3. ใช้คำสั่ง docker-compose up -d เพื่อสร้าง container พร้อมกับเปิดใช้งาน container
4. ใช้คำสั่ง docker-compose stop เพื่อปิดการใช้งาน container
5. เมื่อเปิดใช้งานเสร็จสิ้นให้ใช้งาน dbeaver เข้าใช้งาน database โดยใช้
   url : jdbc:postgresql://localhost:5432/db-pbru-it
   username : admin-pbru-it
   password : 654244141
6. จากนั้น import ข้อมูล sql 

backend
1. cd เข้าไปยัง backend
2. ใช้คำสั่ง ./gradlew bootrun เพื่อ strat server ฝั่ง backend

frontend
1. เมื่อ clone สำเร็จให้ cd เข้าไปยัง frontend
2. ใส่คำสั่ง bun install ที่ cmd หรือ powershell ของ Visual Studio Code
3. เมื่อ build เสร็จสิ้น ให้ใช้คำสั่ง bun run dev เพื่อ strat server ฝั่ง frontend
4. เข้าใช้งานใน browser ที่ลิงค์ http:localhost:3000
5. เข้าสู่ระบบ http:localhost:3000/login โดยใช้งาน User นี้ { email: apirak.soi@mail.pbru.ac.th , password: 654244141 }
