/*******************************************************************************
 *   BSD License
 *    
 *   Copyright (c) 2017, AT&T Intellectual Property.  All other rights reserved.
 *    
 *   Redistribution and use in source and binary forms, with or without modification, are permitted
 *   provided that the following conditions are met:
 *    
 *   1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *      and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *      conditions and the following disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *   3. All advertising materials mentioning features or use of this software must display the
 *      following acknowledgement:  This product includes software developed by the AT&T.
 *   4. Neither the name of AT&T nor the names of its contributors may be used to endorse or
 *      promote products derived from this software without specific prior written permission.
 *    
 *   THIS SOFTWARE IS PROVIDED BY AT&T INTELLECTUAL PROPERTY ''AS IS'' AND ANY EXPRESS OR
 *   IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *   MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 *   SHALL AT&T INTELLECTUAL PROPERTY BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  LOSS OF USE, DATA, OR PROFITS;
 *   OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *   ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 *   DAMAGE.
 *******************************************************************************/

package com.att.idp.foundation.scheduler;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.att.ajsc.logging.AjscEelfManager;
import com.att.eelf.configuration.EELFLogger;
import com.att.idp.foundation.configuration.ApplicationConstants;
import com.att.idp.foundation.configuration.SchedulerConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class SchedulerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration.class })
@ActiveProfiles(profiles = { ApplicationConstants.APPLICATION_PROFILE_SCHEDULER,
		ApplicationConstants.TEST_PROFILE_SCHEDULER })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { SchedulerConfiguration.class })
@SuppressWarnings("PMD.AccessorMethodGeneration")
public class SchedulerTest {

	/** The scheduled task. */
	@Autowired
	private TestScheduledTask scheduledTask;

	/** The log. */
	private static EELFLogger log = AjscEelfManager.getInstance().getLogger(SchedulerTest.class);

	// scheduled methods after, all are executed every 6 seconds
	// (scheduledAtFixedRate and scheduledAtFixedDelay start to execute at
	// application context start, two other methods begin 6 seconds after
	/**
	 * The Class TestScheduledTask.
	 */
	// application's context start)
	@Component
	@Profile(ApplicationConstants.TEST_PROFILE_SCHEDULER)
	public static class TestScheduledTask {

		/** The fixed rate counter. */
		private int fixedRateCounter;

		/** The fixed delay counter. */
		private int fixedDelayCounter;

		/** The initial delay counter. */
		private int initialDelayCounter;

		/** The cron counter. */
		private int cronCounter;

		/**
		 * Scheduled at fixed rate.
		 */
		@Scheduled(fixedRate = 6000)
		public void scheduledAtFixedRate() {
			log.info("<R> Increment at fixed rate");
			fixedRateCounter++;
		}

		/**
		 * Scheduled at fixed delay.
		 */
		@Scheduled(fixedDelay = 6000)

		public void scheduledAtFixedDelay() {
			log.info("<D> Incrementing at fixed delay");
			fixedDelayCounter++;
		}

		/**
		 * Scheduled with initial delay.
		 */
		@Scheduled(fixedDelay = 6000, initialDelay = 6000)
		public void scheduledWithInitialDelay() {
			log.info("<DI> Incrementing with initial delay");
			initialDelayCounter++;
		}

		/**
		 * Scheduled with cron.
		 */
		@Scheduled(cron = "*/6 * * * * *")
		public void scheduledWithCron() {
			log.info("<C> Incrementing with cron");
			cronCounter++;

		}

		/**
		 * Gets the fixed rate counter.
		 *
		 * @return the fixed rate counter
		 */
		public int getFixedRateCounter() {
			return this.fixedRateCounter;
		}

		/**
		 * Gets the fixed delay counter.
		 *
		 * @return the fixed delay counter
		 */
		public int getFixedDelayCounter() {
			return this.fixedDelayCounter;
		}

		/**
		 * Gets the initial delay counter.
		 *
		 * @return the initial delay counter
		 */
		public int getInitialDelayCounter() {
			return this.initialDelayCounter;
		}

		/**
		 * Gets the cron counter.
		 *
		 * @return the cron counter
		 */
		public int getCronCounter() {
			return this.cronCounter;
		}

	}

	/**
	 * Test scheduler.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testScheduler() throws InterruptedException {

		log.info("Start sleeping");
		Thread.sleep(6000);
		log.info("Wake up !");

		/*
		 * Test fixed delay. It's executed every 6 seconds. The first execution
		 * is registered after application's context start.
		 */
		assertSame(
				"Scheduled task should be executed 2 times (1 before sleep in this method, 1 after the sleep), but was "
						+ scheduledTask.getFixedDelayCounter(),
				scheduledTask.getFixedDelayCounter(), 2);

		/*
		 * Test fixed rate. It's executed every 6 seconds. The first execution
		 * is registered after application's context start. Unlike fixed delay,
		 * a fixed rate configuration executes one task with specified time. For
		 * example, it will execute on 6 seconds delayed task at 10:30:30,
		 * 10:30:36, 10:30:42 and so on - even if the task 10:30:30 taken 30
		 * seconds to be terminated. In the case of fixed delay, if the first
		 * task takes 30 seconds, the next will be executed 6 seconds after the
		 * first one, so the execution flow will be: 10:30:30, 10:31:06,
		 * 10:31:12.
		 */
		assertSame(
				"Scheduled task should be executed 2 times (1 before sleep in this method, 1 after the sleep), but was "
						+ scheduledTask.getFixedRateCounter(),
				scheduledTask.getFixedRateCounter(), 2);
		/*
		 * Test fixed rate with initial delay attribute. The initialDelay
		 * attribute is set to 6 seconds. It causes that scheduled method is
		 * executed 6 seconds after application's context start. In our case, it
		 * should be executed only once because of previous Thread.sleep(6000)
		 * invocation.
		 */
		assertSame(
				"Scheduled task should be executed 1 time (0 before sleep in this method, 1 after the sleep), but was "
						+ scheduledTask.getInitialDelayCounter(),
				scheduledTask.getInitialDelayCounter(), 1);
		/*
		 * Test cron scheduled task. Cron is scheduled to be executed every 6
		 * seconds. It's executed only once, so we can deduce that it's not
		 * invoked directly before applications context start, but only after
		 * configured time (6 seconds in our case).
		 */
		assertSame(
				"Scheduled task should be executed 1 time (0 before sleep in this method, 1 after the sleep), but was "
						+ scheduledTask.getCronCounter(),
				scheduledTask.getCronCounter(), 1);
	}

}
