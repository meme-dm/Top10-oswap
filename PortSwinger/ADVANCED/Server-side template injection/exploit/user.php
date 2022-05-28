<?php

class User {
    public $username;
    public $name;
    public $first_name;
    public $nickname;
    public $user_dir;

    public function __construct($username, $name, $first_name, $nickname) {
        $this->username = $username;
        $this->name = $name;
        $this->first_name = $first_name;
        $this->nickname = $nickname;
        $this->user_dir = "users/" . $this->username;
        $this->avatarLink = $this->user_dir . "/avatar";

        if (!file_exists($this->user_dir)) {
            if (!mkdir($this->user_dir, 0755, true))
            {
                throw new Exception("Could not mkdir users/" . $this->username);
            }
        }
    }

    public function setAvatar($filename, $mimetype) {
        if (strpos($mimetype, "image/") !== 0) {
            throw new Exception("Uploaded file mime type is not an image: " . $mimetype);
        }

        if (is_link($this->avatarLink)) {
            $this->rm($this->avatarLink);
        }

        if (!symlink($filename, $this->avatarLink)) {
            throw new Exception("Failed to write symlink " . $filename . " -> " . $this->avatarLink);
        }
    }

    public function delete() {
        $file = $this->user_dir . "/disabled";
        if (file_put_contents($file, "") === false) {
            throw new Exception("Could not write to " . $file);
        }
    }

    public function gdprDelete() {
        $this->rm(readlink($this->avatarLink));
        $this->rm($this->avatarLink);
        $this->delete();
    }

    private function rm($filename) {
        if (!unlink($filename)) {
            throw new Exception("Could not delete " . $filename);
        }
    }
}

?>