--liquibase formatted sql

--changeset chiennv:2

INSERT INTO `notes` (`title`, `content`)
VALUES ('tttttttt1', 'aaa');
INSERT INTO `notes` (`title`, `content`)
VALUES ('tttttttt2', 'bbb');

--changeset chiennv:4

INSERT INTO `colors` (`name`, `code`, `is_default`)
VALUES ('red', '111', 0);
INSERT INTO `colors` (`name`, `code`, `is_default`)
VALUES ('orange', '222', 0);
INSERT INTO `colors` (`name`, `code`, `is_default`)
VALUES ('yellow', '333', 1);
INSERT INTO `colors` (`name`, `code`, `is_default`)
VALUES ('blue', '444', 0);
INSERT INTO `colors` (`name`, `code`, `is_default`)
VALUES ('green', '333', 0);

--changeset chiennv:6
INSERT INTO `note_colors` (`note_id`, `color_id`)
VALUES (1, 1);
INSERT INTO `note_colors` (`note_id`, `color_id`)
VALUES (2, 1);

--changeset chiennv:8
INSERT INTO `labels` (`name`)
VALUES ('label 1');
INSERT INTO `labels` (`name`)
VALUES ('label 2');
INSERT INTO `labels` (`name`)
VALUES ('label 3');
INSERT INTO `labels` (`name`)
VALUES ('label 4');
INSERT INTO `labels` (`name`)
VALUES ('label 5');
INSERT INTO `labels` (`name`)
VALUES ('label 6');

INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (1, 1);
INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (1, 2);
INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (1, 3);
INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (2, 4);
INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (2, 5);
INSERT INTO `note_labels` (`note_id`, `label_id`)
VALUES (2, 1);

