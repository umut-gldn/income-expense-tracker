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

### Gelir İşlemleri

| Yöntem | Endpoint       | Açıklama          |
| ------ | -------------- | ----------------- |
| POST   | `/api/incomes` | Gelir ekle        |
| GET    | `/api/incomes` | Gelirleri listele |

Gelir ekleme isteği

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

### Gider İşlemleri

| Yöntem | Endpoint        | Açıklama          |
| ------ | --------------- | ----------------- |
| POST   | `/api/expenses` | Gider ekle        |
| GET    | `/api/expenses` | Giderleri listele |

### Özet

`GET /api/summary` — toplam gelir, gider ve bakiye

```json
{
  "totalIncome": 5000.0,
  "totalExpense": 120.75,
  "balance": 4879.25
}
```

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
