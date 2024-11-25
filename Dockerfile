# Sử dụng Maven để build dự án
FROM maven:latest AS build
WORKDIR /app
# Sao chép tất cả các file vào thư mục làm việc
COPY . .
# Chạy Maven để build dự án, bỏ qua kiểm tra test (optional)
RUN mvn clean package -DskipTests

# Sử dụng Amazon Corretto để chạy ứng dụng
FROM amazoncorretto:17
WORKDIR /app
# Sao chép file WAR đã build từ image trước vào image này
COPY --from=build /app/target/yoga-business-management-web.war yoga-business-management-web

# Mở cổng 8080 để chạy ứng dụng
EXPOSE 8080

# Chạy ứng dụng bằng lệnh Java
ENTRYPOINT ["java", "-jar", "yoga-business-management-web.war"]
