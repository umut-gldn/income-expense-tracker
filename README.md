# Gelir & Gider Takip API’si

Spring Boot 3 ile geliştirilen bu RESTful servis, kullanıcıların **gelir** ve **gider** hareketlerini kaydetmesine ve anlık bakiye hesaplamasına imkân tanır. Tüm korumalı isteklere **JWT** ile kimlik doğrulama uygulanır; her kullanıcı yalnızca kendi verilerini görebilir.

---

## Başlıca Özellikler

- **Kullanıcı Kaydı / Girişi** — stateless JWT ve token yenileme
- **Gelir & Gider CRUD** — kayıt oluşturma, listeleme, güncelleme, silme
- **Anlık Özet** — toplam gelir, gider ve bakiye
- **Doğrulama & Global Hata Yönetimi** — tutarlı JSON hata gövdesi
- **DTO ↔ Entity Dönüşümleri** — MapStruct ile sıfır boilerplate
- **Swagger UI** — springdoc-openapi sayesinde etkileşimli dokümantasyon
- **Temiz Katmanlı Mimari** — controller-service-repository ayrımı

---

## Teknoloji Yığını

| Katman        | Teknoloji                    |
| ------------- | ---------------------------- |
| Dil           | Java 17                      |
| Çatı          | Spring Boot                  |
| Güvenlik      | Spring Security + JJWT       |
| Veri          | Spring Data JPA + PostgreSQL |
| DTO Mapleme   | MapStruct                    |
| Dokümantasyon | Swagger                      |
| Yardımcı      | Lombok                       |
| Derleme       | Maven                        |

---

## API Hızlı Tur

### Kimlik Doğrulama

| Yöntem | Endpoint             | Açıklama             |
| ------ | -------------------- | -------------------- |
| POST   | `/api/auth/register` | Yeni kullanıcı kaydı |
| POST   | `/api/auth/login`    | JWT token al         |

Kayıt isteği örneği

```json
{
  "firstName": "Umut",
  "lastName": "Gülden",
  "username": "umutgldn",
  "password": "123456"
}
```

Başarılı giriş yanıtı

```json
{ "token": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

Header’a `Authorization: Bearer <token>` ekleyerek korumalı uç noktalara erişin.

### Gelir (Income) İşlemleri

| Yöntem | Endpoint | Açıklama |
| ------ | -------- | -------- |
| POST   | `/api/incomes` | Gelir ekle |
| GET    | `/api/incomes` | Gelirleri listele |
| PUT    | `/api/incomes/{id}` | Gelir güncelle |
| DELETE | `/api/incomes/{id}` | Gelir sil |
| GET    | `/api/incomes/total` | Kullanıcıya ait toplam bakiye|

Örnek istek (POST `/api/incomes`)
```json
{
  "amount": 5000.0,
  "description": "Maaş",
  "date": "2025-06-01",
  "category": "Salary"
}
```
Olası yanıt (201 Created)
```json
{
  "id": 1,
  "amount": 5000.0,
  "description": "Maaş",
  "date": "2025-06-01",
  "category": "Salary"
}
```

### Gider (Expense) İşlemleri

| Yöntem | Endpoint | Açıklama |
| ------ | -------- | -------- |
| POST   | `/api/expenses` | Gider ekle |
| GET    | `/api/expenses` | Giderleri listele |
| PUT    | `/api/expenses/{id}` | Gider güncelle |
| DELETE | `/api/expenses/{id}` | Gider sil |
| GET    | `/api/expenses/total` | Kullanıcıya ait toplam bakiye |

### Toplam Bakiye
`GET /api/incomes/total` ve `GET /api/expenses/total` uç noktalarından  Örnek yanıt:
```json
{
  "totalIncome": 5000.0,
  "totalExpense": 120.75,
  "balance": 4879.25
}
```

### Tüm Uç Noktaların Özeti

| Grup | Endpoint | Yöntemler |
|------|----------|-----------|
| Auth | `/api/auth/register` | POST |
| Auth | `/api/auth/login`    | POST |
| Incomes | `/api/incomes` | POST, GET |
| Incomes | `/api/incomes/{id}` | PUT, DELETE |
| Incomes | `/api/incomes/total` | GET |
| Expenses | `/api/expenses` | POST, GET |
| Expenses | `/api/expenses/{id}` | PUT, DELETE |
| Expenses | `/api/expenses/total` | GET |

### Hata Formatı Örneği

Kullanıcı adı alınmışsa 400 Bad Request gelir:

```json
{
  "message": "Username is already taken",
  "status": 400,
  "timestamp": "2025-06-24T14:32:00"
}
```

---

## Proje Dizini

```
src
├── config        # güvenlik & genel konfigürasyon
├── controller    # REST uç noktaları
├── dto           # istek / yanıt modelleri
├── entity        # JPA varlıkları
├── mapper        # MapStruct dönüştürücüler
├── repository    # veri erişim katmanı
├── service       # iş mantığı
└── exception     # özel istisnalar & handler’lar
```
