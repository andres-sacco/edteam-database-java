CREATE TABLE flights_reservation.reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creation_date DATE,
    itinerary_id INT
);


INSERT INTO `flights_reservation`.`reservation` (`creation_date`, `itinerary_id`) VALUES ('2023-01-01', '1');


INSERT INTO `flights_reservation`.`reservation` (`creation_date`, `itinerary_id`) VALUES ('2023-01-01', '2');


INSERT INTO `flights_reservation`.`reservation` (`creation_date`, `itinerary_id`) VALUES ('2023-01-01', '3');

