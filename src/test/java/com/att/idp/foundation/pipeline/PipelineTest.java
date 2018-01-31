package com.att.idp.foundation.pipeline;

import org.junit.Test;

import com.att.ajsc.logging.AjscEelfManager;
import com.att.eelf.configuration.EELFLogger;
import com.att.idp.foundation.scheduler.SchedulerTest;

// TODO: Auto-generated Javadoc
/**
 * The Class PipelineTest.
 */
@SuppressWarnings({"PMD.AccessorMethodGeneration","PMD.GuardLogStatementJavaUtil","PMD.GuardLogStatement","PMD.ShortVariable"})
public class PipelineTest {

	/** The log. */
	private static EELFLogger log = AjscEelfManager.getInstance().getLogger(SchedulerTest.class);
	
	/** The Constant AMOUNT_IS. */
	private static final String AMOUNT_IS = " amount is ";
	
	/**
	 * The Class PurchaseRequest.
	 */
	private static class PurchaseRequest {
		
		/** The amount. */
		private double amount;

		/**
		 * Instantiates a new purchase request.
		 *
		 * @param amount the amount
		 */
		public PurchaseRequest(final double amount) {
			this.amount = amount;
		}

		/**
		 * Gets the amount.
		 *
		 * @return the amount
		 */
		public double getAmount() {
			return amount;
		}

		/**
		 * Sets the amount.
		 *
		 * @param amount the new amount
		 */
		public void setAmount(final double amount) {
			this.amount = amount;
		}
	}

	/**
	 * The Class InitialProcessor.
	 */
	private static class InitialProcessor implements Processor<PurchaseRequest> {
		
		/**
		 * execute.
		 *
		 * @param request request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + " Amount=" + request.amount);
		}

	}

	/**
	 * The Class AddAmountProcessor.
	 */
	private static class AddAmountProcessor implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			final double amount = request.getAmount() + 100;
			request.setAmount(amount);
			log.info(this.getClass().getName() + " Amount=" + request.amount);
		}

	}

	/**
	 * The Class AskApprovalProcessor.
	 */
	@SuppressWarnings("unused")
	private static class AskApprovalProcessor implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + " asking for approval.");
		}

	}

	/**
	 * The Class GrantApprovalProcessor.
	 */
	private static class GrantApprovalProcessor implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + " approval granted.");
		}

	}

	/**
	 * The Class MakeDefaultCard.
	 */
	private static class MakeDefaultCard implements Processor<PurchaseRequest> {
		
		

		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " making default card.");
		}

	}

	/**
	 * The Class SendDefaultCard.
	 */
	private static class SendDefaultCard implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " sending default card.");
		}

	}

	/**
	 * The Class MakeSilverCard.
	 */
	private static class MakeSilverCard implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " making silver card.");
		}

	}

	/**
	 * The Class SendSilverCard.
	 */
	private static class SendSilverCard implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " sending silver card.");
		}

	}

	/**
	 * The Class MakeGoldCard.
	 */
	private static class MakeGoldCard implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " making gold card.");
		}
	}

	/**
	 * The Class SendGoldCard.
	 */
	private static class SendGoldCard implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + AMOUNT_IS + request.getAmount() + " sending gold card.");
		}
	}

	/**
	 * The Class NotifyCustomer.
	 */
	private static class NotifyCustomer implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + " notifying customer");
		}
	}

	/**
	 * The Class UpdateCustomerRecord.
	 */
	private static class UpdateCustomerRecord implements Processor<PurchaseRequest> {
		
		/* (non-Javadoc)
		 * @see com.att.idp.foundation.pipeline.Processor#execute(java.lang.Object)
		 */
		/**
		 * execute.
		 *
		 * @param request the request
		 */
		@Override
		public void execute(final PurchaseRequest request) {
			log.info(this.getClass().getName() + " updating customer record.");
		}
	}

	/**
	 * Test.
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	public void test() {
		@SuppressWarnings("PMD.SystemPrintln")
		final PipelineBuilder<PurchaseRequest> approvalBranchPipeline = PipelineBuilder.<PurchaseRequest>builder()
				// lambda expressions can also be used
				.first(p -> System.out.println("Asking for approval amount=" + p.amount)).next(new GrantApprovalProcessor());

		final PipelineBuilder<PurchaseRequest> silverCardBranchPipeline = PipelineBuilder.<PurchaseRequest>builder()
				.first(new MakeSilverCard()).branch(approvalBranchPipeline).join().next(new SendSilverCard());

		final PipelineBuilder<PurchaseRequest> goldCardBranchPipeline = PipelineBuilder.<PurchaseRequest>builder()
				.first(new MakeGoldCard()).branch(approvalBranchPipeline).join().next(new SendGoldCard());

		final PipelineBuilder<PurchaseRequest> defaultCardBranchPipeline = PipelineBuilder.<PurchaseRequest>builder()
				.first(new MakeDefaultCard()).branch(approvalBranchPipeline).join().next(new SendDefaultCard());

		final Pipeline<PurchaseRequest> mainPipeline = PipelineBuilder.<PurchaseRequest>builder()
				.first(new InitialProcessor()).next(new AddAmountProcessor())
				.branch(silverCardBranchPipeline, p -> p.amount > 2000 && p.amount <= 3000)
				.branch(goldCardBranchPipeline, p -> p.amount > 3000)
				// if none of above condition matches the execute default branch
				.branch(defaultCardBranchPipeline).join()
				// After join continue the pipeline chain
				.next(new NotifyCustomer()).next(new UpdateCustomerRecord()).build();

		mainPipeline.execute(new PurchaseRequest(3000));
		log.info("----------------------------");
		mainPipeline.execute(new PurchaseRequest(2000));
		log.info("----------------------------");
		mainPipeline.execute(new PurchaseRequest(1000));
	}

}
