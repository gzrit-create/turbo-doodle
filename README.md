# :D 记账本

## 1. 项目说明

本项目是一个基于 **Spring Boot 3.2.5 + MyBatis-Plus + MySQL 8.0** 的简易记账本。提供用户注册、JWT 登录、账单管理、收支统计、管理员用户管理等功能。  (￣▽￣)~*

## 2. 数据库设计说明

### 2.1 ER 图（简要）

- **user**：用户表，存储账号、密码、昵称、状态、角色。
- **bill**：账单表，存储用户每笔收支记录，关联分类。

### 2.2 表结构

#### user 表
| 字段       | 类型          | 说明                   |
|-----------|---------------|------------------------|
| id        | bigint        | 主键，自增              |
| username  | varchar(50)   | 用户名，唯一            |
| password  | varchar(255)  | 密码（明文或加密）      |
| nickname  | varchar(50)   | 昵称                   |
| status    | tinyint       | 1-正常，0-禁用         |
| role      | varchar(20)   | USER / ADMIN           |

#### bill 表
| 字段        | 类型           | 说明                     |
|-------------|----------------|--------------------------|
| id          | bigint         | 主键，自增               |
| user_id     | bigint         | 关联 user.id             |
| category_id | int            | 分类ID（可后续扩展）     |
| amount      | decimal(10,2)  | 金额                     |
| note        | varchar(255)   | 备注                     |
| bill_date   | date           | 账单日期                 |
| type        | tinyint        | 1-收入，2-支出           |

## 3. 配置说明

### 3.1 环境要求
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 3.2 配置文件
项目使用 `application.yml` 作为配置文件。

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/account_book?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: ${DB_PASSWORD}   # 使用环境变量
    driver-class-name: com.mysql.cj.jdbc.Driver
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
