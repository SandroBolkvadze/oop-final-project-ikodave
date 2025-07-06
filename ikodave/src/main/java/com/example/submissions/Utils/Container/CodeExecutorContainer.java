package com.example.submissions.Utils.Container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.*;
import java.nio.file.Path;
import java.util.List;

public class CodeExecutorContainer {

    private static final String DOCKER_IMAGE = "sandbox-code-executor";

    private static final String CONTAINER_PATH = "/app";

    private String containerId;

    private final Path hostPath;

    public CodeExecutorContainer(Path hostPath) {
        this.hostPath = hostPath;
    }

    public void createContainer(DockerClient dockerClient) {
        Volume containerVolume = new Volume(CONTAINER_PATH);
        Bind bind = new Bind(hostPath.toString(), containerVolume, AccessMode.rw);

        HostConfig hostConfig = new HostConfig()
                .withReadonlyRootfs(true)
                .withNetworkMode("none")
                .withMemory(256L * 1024 * 1024)
                .withCpuCount(4L)
                .withCapDrop(Capability.values())
                .withSecurityOpts(List.of("no-new-privileges"))
                .withBinds(bind);

        CreateContainerResponse response = dockerClient.createContainerCmd(DOCKER_IMAGE)
                .withHostConfig(hostConfig)
                .withWorkingDir(CONTAINER_PATH)
                .withCmd("sleep", "infinity")
                .exec();

        containerId = response.getId();


        dockerClient.startContainerCmd(containerId)
                .exec();
    }


    public void destroyContainer(DockerClient dockerClient) {
        dockerClient.removeContainerCmd(containerId)
                .withForce(true)
                .exec();
    }


    public void cleanContainer(DockerClient dockerClient) {
        ExecCreateCmdResponse execCreate = dockerClient.execCreateCmd(containerId)
                .withCmd("bash", "-lc",  "rm -rf /app/* /app/.* 2>/dev/null || true")
                .exec();

        try {
            dockerClient.execStartCmd(execCreate.getId())
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            // IGNORE PAYLOAD
                        }
                    }).awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String getContainerId() {
        return containerId;
    }

    public Path getHostPath() {
        return hostPath;
    }


}
