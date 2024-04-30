\c spotitube;
-- Inserting test data for tracks table
INSERT INTO tracks (TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATION_DATE, DESCRIPTION, OFFLINE_AVAILABLE)
VALUES ('Song Title 1', 'Performer 1', 240, 'Album 1', 100, '2023-01-15', 'Description for Song 1', TRUE),
       ('Song Title 2', 'Performer 2', 180, 'Album 2', 50, '2023-02-20', 'Description for Song 2', FALSE),
       ('Song Title 3', 'Performer 3', 300, 'Album 3', 75, '2022-12-10', 'Description for Song 3', TRUE),
       ('Song Title 4', 'Performer 1', 200, 'Album 1', 90, '2023-05-05', 'Description for Song 4', TRUE),
       ('Song Title 5', 'Performer 4', 220, 'Album 4', 120, '2023-08-12', 'Description for Song 5', FALSE);

-- Inserting test data for users table
INSERT INTO users (USERNAME, PASSWORD, TOKEN)
VALUES ('user1', 'password1', '111'),
       ('user2', 'password2', '222'),
       ('user3', 'password3', '333'),
       ('Admin', 'Admin', 'Admin');


-- Inserting test data for playlists table
INSERT INTO playlists (NAME, OWNER)
VALUES ('Playlist 1', 1),
       ('Playlist 2', 1),
       ('Playlist 3', 2);

-- Inserting test data for tracksInPlaylist table
INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 3);


INSERT INTO playlists (NAME, OWNER)
VALUES ('Playlist 1', 1);
INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)
VALUES (1, 4),
       (2, 4);


INSERT INTO tracks (TITLE, PERFORMER, DURATION, ALBUM, PLAYCOUNT, PUBLICATION_DATE, DESCRIPTION, OFFLINE_AVAILABLE)
VALUES
  ('Take On Me', 'a-ha', 227, 'Hunting High and Low', 100, '1984-10-19', 'Iconic 80s synth-pop hit', true),
  ('Billie Jean', 'Michael Jackson', 293, 'Thriller', 150, '1983-01-02', 'Legendary track by the King of Pop', true),
  ('Every Breath You Take', 'The Police', 243, 'Synchronicity', 120, '1983-05-20', 'Classic song with a haunting melody', false);


