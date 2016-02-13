package com.hackerearth.competitions.shortcompetitions.encodethebeginning;

import java.io.*;
import java.util.*;

class Q3
{
	static int t, n, m;
	static List<Pair>[] list;
	static boolean[] visited;
	static Scanner in;
//	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args) throws FileNotFoundException
	{
//		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive " +
			"Programming/src/com/test/input5.txt"));
		
		solve();
		
		out.flush();
		
//		in.close();
		out.close();
	}

	static void solve()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			m = in.nextInt();

			System.out.println("t : " + t + ", n : " + n + ", m : " + m);

			createGraph(n, m);
			
			int town = in.nextInt() - 1;
			
			Group[] groups = new Group[n];
			int groupCount = 0;
			
			for (int i = 0; i < n; i++)
			{
				groups[groupCount++] = countBananas(i);
			}
			
			Arrays.sort(groups, 0, groupCount, new Comparator<Group>()
			{
				@Override
				public int compare(Group o1, Group o2)
				{
					return Long.compare(o1.weight, o2.weight);
				}
			});
			
			if (groupCount == 0)
			{
				out.println("YES");
				
				return;
			}
			
			Group largest = groups[groupCount - 1];
			
//			System.out.println(largest.list.toString());
			
/*			for (int j = 0; j < groupCount; j++)
			{
				System.out.println((groups[j].list == null ? "... " : groups[j].list.toString()));
				System.out.println(" : " + groups[j].weight);
			}*/
			
			if (largest == null)
			{
				out.println("YES");
				continue;
			}
			
			boolean ans;
			
			if (largest.list != null)
				ans = largest.list.contains(town);
			else
				ans = false;
			
			if (ans)
				out.println("NO");
			else
				out.println("YES");
		}
	}
	
	@SuppressWarnings("unchecked")
	static void createGraph(int nodes, int edges)
	{
//		list = new ArrayList[nodes];
//		visited = new boolean[nodes];

		list = new ArrayList[(int) 1e5 + 5];
		visited = new boolean[(int) 1e5 + 5];
		
		for (int i = 0; i < edges; i++)
		{
			int from, to, weight;
			
			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			weight = in.nextInt();
			
			if (list[from] == null)
				list[from] = new ArrayList<Pair>();
			
			list[from].add(new Pair(to, weight));
			
			if (list[to] == null)
				list[to] = new ArrayList<Pair>();
			
			list[to].add(new Pair(from, weight));
		}
	}
	
	static Group countBananas(int node)
	{
		if (visited[node])
			return new Group(null, -1);
		
		if (list[node] == null/* || list[node].size() == 0*/)
			return new Group(null, 0);
		
		Stack<Pair> stack = new Stack<Pair>();
		Group group = new Group(new ArrayList<Integer>(), 0);
		
		visited[node] = true;
		stack.push(new Pair(node, 0));
		group.list.add(node);
		
		long total = 0;

		Iterator<Pair> iterator = list[node].iterator();

		while (iterator.hasNext())
		{
			Pair curr = iterator.next();

			if (!visited[curr.node])
			{
				stack.push(curr);
				group.list.add(curr.node);
			}
		}

		while (stack.size() > 1)
		{
			Pair top = stack.pop();
			
			if (visited[top.node])
				continue;
			
			visited[top.node] = true;
			total += top.weight;

			if (list[top.node] == null)
				continue;
			
			iterator = list[top.node].iterator();

			while (iterator.hasNext())
			{
				Pair curr = iterator.next();

				if (!visited[curr.node])
				{
					stack.push(curr);
					group.list.add(curr.node);
				}
			}
		}
		
		stack.pop();
		group.weight = total;
		
		return group;
	}
	
	static class Pair
	{
		int node, weight;

		public Pair(int node, int weight)
		{
			this.node = node;
			this.weight = weight;
		}
		
	}
	
	static class Group
	{
		List<Integer> list;
		long weight;
		
		public Group(List<Integer> list, long weight)
		{
			this.list = list;
			this.weight = weight;
		}
		
	}
	
	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}
		
		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];
			
			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();
			
			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}
		
		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];
			
			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();
			
			return array;
		}

		public float nextFloat() // problematic
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble() // not completely accurate
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
		
		public String nextLine()
		{
			int c = read();
			
			StringBuilder result = new StringBuilder();
			
			do
			{
				result.appendCodePoint(c);
				
				c = read();
			} while (!isNewLine(c));
			
			return result.toString();
		}
		
		public boolean isNewLine(int c)
		{
			return c == '\n';
		}

		public void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}
