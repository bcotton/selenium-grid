#!/usr/bin/env ruby

def parse_header(html)
  html =~ /(.*<div class="results">)/mi
  $1
end

def parse_footer(html)
  html[/<\/div>\s*<\/body>.*/mi]
end

def parse_body(html)
  html =~ /<div class="results">(.*)<\/div>\s*<\/body>/mi
  content = $1
end

aggregated_body = ""
header = nil
footer = nil
ARGV.each do |file|
  lines = IO.readlines(file)
  html = lines.join(' ')
  header ||= parse_header(html)
  footer ||= parse_footer(html)
  aggregated_body << parse_body(html).to_s
end

STDOUT.puts header
STDOUT.puts aggregated_body
STDOUT.puts footer
