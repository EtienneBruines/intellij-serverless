package work.bruines.serverless;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.YAMLTokenTypes;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.yaml.psi.YAMLKeyValue;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerlessCompletionContributor extends CompletionContributor {
    private static final Logger log = Logger.getInstance("Serverless");

    private static ArrayList<String> getPath(PsiElement e) {
        ArrayList<String> list = new ArrayList<>();
        PsiElement parent = e.getParent();
        while (parent != null) {
            if (parent instanceof YAMLKeyValue) {
                list.add(((YAMLKeyValue) parent).getKeyText());
            }
            parent = parent.getParent();
        }
        return list;
    }

    private static ArrayList<String> getCompletions(ArrayList<String> path) {
        switch (path.size()) {
            case 0:
                return new ArrayList<>(Arrays.asList("service", "frameworkVersion", "provider", "package", "functions", "resources"));
            case 1:
                switch (path.get(0)) {
                    case "service":
                        return new ArrayList<>(Arrays.asList("name", "awsKmsKeyArn"));
                    case "provider":
                        return new ArrayList<>(Arrays.asList("name", "runtime", "stage", "region", "profile", "memorySize", "timeout", "logRetentionInDays", "deploymentBucket", "role", "cfnRole", "versionFunctions", "environment", "endpointType", "apiKeys", "apiGateway", "usagePlan", "stackTags", "iamRoleStatements", "stackPolicy", "vpc"));
                    case "package":
                        return new ArrayList<>(Arrays.asList("include", "exclude", "excludeDevDependencies"));
                    case "functions":
                        return new ArrayList<>(); // custom function names
                    case "resources":
                        return new ArrayList<>(Arrays.asList("Resources", "Outputs"));
                }
        }
        return new ArrayList<>();
    }

    public ServerlessCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(YAMLTokenTypes.TEXT).withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (String option : getCompletions(getPath(parameters.getPosition()))) {
                            resultSet.addElement(LookupElementBuilder.create(option));
                        }
                    }
                }
        );

    }
}
