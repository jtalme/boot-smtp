package com.tallstak.bootsmtp.server;

import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, Long> {

}
