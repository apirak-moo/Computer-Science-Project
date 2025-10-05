# 🎓 Computer Science Project

โปรเจกต์นี้เป็นส่วนหนึ่งของรายวิชา... (เพิ่มคำอธิบายสั้นๆ เกี่ยวกับโปรเจกต์ของคุณที่นี่) สำหรับนักศึกษาคณะเทคโนโลยีสารสนเทศ สาขาวิทยาการคอมพิวเตอร์ มหาวิทยาลัยราชภัฏเพชรบุรี

---

## 🚀 เริ่มต้นใช้งาน (Getting Started)

คำแนะนำเหล่านี้จะช่วยให้คุณสามารถติดตั้งและรันโปรเจกต์นี้บนเครื่องของคุณได้

### ✅ สิ่งที่ต้องมี (Prerequisites)

ก่อนเริ่มต้น ตรวจสอบให้แน่ใจว่าคุณได้ติดตั้งซอฟต์แวร์ต่อไปนี้แล้ว:

- **Docker Desktop:** [ดาวน์โหลด](https://www.docker.com/products/docker-desktop/)
- **Bun:** [ติดตั้ง](https://bun.sh/docs/installation)
- **Java Development Kit (JDK):** แนะนำเวอร์ชัน 21 หรือสูงกว่า [ดาวน์โหลด](https://adoptium.net/)
- **DBeaver (หรือโปรแกรมจัดการฐานข้อมูลอื่น):** [ดาวน์โหลด](https://dbeaver.io/download/)

---

## 🛠️ การติดตั้งและรันโปรเจกต์ (Installation and Running)

ทำตามขั้นตอนต่อไปนี้เพื่อตั้งค่าสภาพแวดล้อมสำหรับการพัฒนา

### 📦 ฐานข้อมูล (Database)

1.  **เปิด Docker Desktop**
2.  ใน Terminal/CMD ให้ไปที่ path ที่มีไฟล์ `docker-compose.yml`
3.  รันคำสั่งเพื่อสร้างและเปิดใช้งาน Container:
    ```bash
    docker-compose up -d
    ```
4.  เชื่อมต่อฐานข้อมูลผ่าน DBeaver โดยใช้ข้อมูลต่อไปนี้:
    - **URL:** `jdbc:postgresql://localhost:5432/db-pbru-it`
    - **Username:** `admin-pbru-it`
    - **Password:** `654244141`
5.  นำเข้า (Import) ข้อมูลจากไฟล์ SQL ที่อยู่ในโฟลเดอร์:
    ```
    /data/dump-db-pbru-it-202508040009.sql
    ```
6.  หากต้องการหยุดการทำงานของ Container ให้ใช้คำสั่ง:
    ```bash
    docker-compose stop
    ```

### ⚙️ เซิร์ฟเวอร์หลังบ้าน (Backend)

1.  เข้าไปยังโฟลเดอร์ `backend`:
    ```bash
    cd backend
    ```
2.  รันเซิร์ฟเวอร์ด้วยคำสั่ง Gradle Wrapper:
    ```bash
    ./gradlew bootRun
    ```
    เซิร์ฟเวอร์จะทำงานที่ `http://localhost:8080`

### 🖥️ ระบบหน้าบ้าน (Frontend)

1.  เข้าไปยังโฟลเดอร์ `frontend`:
    ```bash
    cd frontend
    ```
2.  ติดตั้ง Dependencies ด้วย Bun:
    ```bash
    bun install
    ```
3.  เมื่อติดตั้งเสร็จสิ้น ให้รันเซิร์ฟเวอร์สำหรับพัฒนา:
    ```bash
    bun run dev
    ```
4.  เปิดเว็บเบราว์เซอร์แล้วไปที่: [http://localhost:3000](http://localhost:3000)

---

## 🔑 การเข้าสู่ระบบ (Login)

คุณสามารถเข้าสู่ระบบเพื่อทดสอบได้ที่หน้า [Login](http://localhost:3000/login) โดยใช้ข้อมูลด้านล่าง:

- **อีเมล:** `apirak.soi@mail.pbru.ac.th`
- **รหัสผ่าน:** `654244141`
