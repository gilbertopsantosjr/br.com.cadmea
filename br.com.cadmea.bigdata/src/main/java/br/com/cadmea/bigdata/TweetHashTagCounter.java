package br.com.cadmea.bigdata;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TweetHashTagCounter {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();
		String[] myArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (myArgs.length != 2) {
			System.err.println("Usage: TweetHashTagCounter <input path> <output path>");
			System.exit(-1);
		}
		Job job =  Job.getInstance(conf, "Tweet Hash Tag Counter");
		job.setJarByClass(TweetHashTagCounter.class);

		FileInputFormat.addInputPath(job, new Path(myArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(myArgs[1]));

		job.setMapperClass(TweetCountMapper.class);
		job.setReducerClass(IntSumReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}